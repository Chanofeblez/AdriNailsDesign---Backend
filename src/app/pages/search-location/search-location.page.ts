/*
  Authors : initappz (Rahul Jograna)
  Website : https://initappz.com/
  App Name : Salon - 2 This App Template Source code is licensed as per the
  terms found in the Website https://initappz.com/license
  Copyright and Good Faith Purchasers © 2023-present initappz.
*/
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { UtilService } from 'src/app/services/util.service';
declare var google: any;

@Component({
  selector: 'app-search-location',
  templateUrl: './search-location.page.html',
  styleUrls: ['./search-location.page.scss'],
})
export class SearchLocationPage implements OnInit {
  @ViewChild('map', { static: true }) mapElement: ElementRef;
  constructor(
    public util: UtilService
  ) {
    setTimeout(() => {
      this.getULocation();
    }, 1000);
  }

  onBack() {
    this.util.onBack();
  }

  ngOnInit() {
  }

  getULocation() {
    let map: { mapTypes: { set: (arg0: string, arg1: any) => void; }; setMapTypeId: (arg0: string) => void; };

    var centerCords = {
      lat: 21.5346,
      lng: 71.8275
    };
    initMap();

    function initMap() {
      var style = [
        {
          featureType: 'all',
          elementType: 'all',
          stylers: [
            { saturation: -100 }
          ]
        }
      ];

      var mapOptions = {
        zoom: 10,
        scaleControl: false,
        streetViewControl: false,
        zoomControl: false,
        overviewMapControl: false,
        center: centerCords,
        mapTypeControl: false,
        mapTypeControlOptions: {
          mapTypeIds: [google.maps.MapTypeId.ROADMAP, 'nepate']
        },
        disableDefaultUI: true
      }

      map = new google.maps.Map(document.getElementById('map'), mapOptions);
      var mapType = new google.maps.StyledMapType(style, { name: 'Grayscale' });
      map.mapTypes.set('nepate', mapType);
      map.setMapTypeId('nepate');
    }

  }

  onHome() {
    this.util.navigateRoot('/home');
  }

}
