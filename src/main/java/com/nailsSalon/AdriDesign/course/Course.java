package com.nailsSalon.AdriDesign.course;

import com.nailsSalon.AdriDesign.video.Video;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Course {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private String title;

  private String description;

  private BigDecimal price;

  @Column(nullable = false)
  private String imagePath;  // Ruta de la imagen
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "course_video_paths", joinColumns = @JoinColumn(name = "course_id"))
  @Column(name = "videoPaths")
  private List<String> videoPaths;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "course_pdf_paths", joinColumns = @JoinColumn(name = "course_id"))
  @Column(name = "pdfPaths")
  private List<String> pdfPaths;


  @Enumerated(EnumType.STRING)
  private CourseStatus status;

  // Getters and Setters
}


enum CourseStatus {
    ACTIVE, INACTIVE
}

