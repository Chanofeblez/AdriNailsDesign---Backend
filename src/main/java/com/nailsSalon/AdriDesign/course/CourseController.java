package com.nailsSalon.AdriDesign.course;

import com.nailsSalon.AdriDesign.appointment.AppointmentController;
import com.nailsSalon.AdriDesign.video.Video;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = {"http://localhost:8100", "https://adrinailsdesign-c393e5baf34a.herokuapp.com"})
public class CourseController {

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private CourseService courseService;

    // Crear un curso con posibilidad de subir imagen y PDF
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Course createCourse(
            @RequestPart("course") Course course,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestPart(value = "pdf", required = false) MultipartFile pdf,
            @RequestPart(value = "videos", required = false) List<MultipartFile> videos,
            HttpServletRequest request) {

        logger.info("course: {}", course);

        // Medir el tamaño del contenido
        int contentLength = request.getContentLength();
        logger.info("Tamaño del payload recibido: {} bytes", contentLength);

        // Subir y almacenar la imagen
        if (image != null && !image.isEmpty()) {
            String imageUrl = courseService.uploadFile(image);  // Servicio para manejar la subida
            course.setImageUrl(imageUrl);  // Guarda la URL de la imagen en el curso
        }

        // Subir y almacenar el PDF
        if (pdf != null && !pdf.isEmpty()) {
            String pdfUrl = courseService.uploadFile(pdf);  // Servicio para manejar la subida
            course.setPdfUrl(pdfUrl);  // Guarda la URL del PDF en el curso
        }

        // Subir y almacenar los videos
        if (videos != null && !videos.isEmpty()) {
            List<Video> videoEntities = new ArrayList<>();
            for (MultipartFile videoFile : videos) {
                String videoUrl = courseService.uploadFile(videoFile);  // Servicio para manejar la subida de cada video
                Video video = new Video();  // Suponiendo que tienes una entidad `Video`
                video.setUrl(videoUrl);
                video.setCourse(course);  // Establece la relación entre el video y el curso
                videoEntities.add(video);  // Agrega el video a la lista de videos
            }
            course.setVideos(videoEntities);  // Asigna la lista de videos al curso
        }

        return courseService.createCourse(course);  // Guarda el curso con los videos, imagen y PDF
    }


    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable UUID id) {
        Optional<Course> course = courseService.getCourseById(id);
        return course.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/videos")
    public ResponseEntity<List<Video>> accessCourseVideos(@PathVariable UUID id, @RequestParam UUID userId) {
        if (courseService.verifyPayment(id, userId)) {
            Optional<Course> course = courseService.getCourseById(id);
            return course.map(c -> ResponseEntity.ok(c.getVideos())).orElse(ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.status(403).build(); // 403 Forbidden if the user hasn't paid
        }
    }

    // Actualizar un curso (puede incluir nueva imagen o PDF)
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(
            @PathVariable UUID id,
            @RequestPart("course") Course course,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestPart(value = "pdf", required = false) MultipartFile pdf) {

        Optional<Course> existingCourse = courseService.getCourseById(id);
        if (existingCourse.isPresent()) {

            // Subir nueva imagen si se proporciona
            if (image != null && !image.isEmpty()) {
                String imageUrl = courseService.uploadFile(image);
                course.setImageUrl(imageUrl);
            }

            // Subir nuevo PDF si se proporciona
            if (pdf != null && !pdf.isEmpty()) {
                String pdfUrl = courseService.uploadFile(pdf);
                course.setPdfUrl(pdfUrl);
            }

            course.setId(id);  // Asegurar que estamos actualizando el curso correcto
            return ResponseEntity.ok(courseService.updateCourse(course));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable UUID id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
