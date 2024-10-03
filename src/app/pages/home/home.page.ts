/*
  Authors : initappz (Rahul Jograna)
  Website : https://initappz.com/
  App Name : Salon - 2 This App Template Source code is licensed as per the
  terms found in the Website https://initappz.com/license
  Copyright and Good Faith Purchasers © 2023-present initappz.
*/
import { Component, OnInit } from '@angular/core';
import { UtilService } from 'src/app/services/util.service';
import { NavigationExtras } from '@angular/router';
import { register } from 'swiper/element';

register();
@Component({
  selector: 'app-home',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
})
export class HomePage implements OnInit {
  slideOpts = {
    initialSlide: 0,
    slidesPerView: 1.2,
  };
  slideOptsCategories = {
    initialSlide: 0,
    slidesPerView: 4.5,
  };
  constructor(
    public util: UtilService
  ) { }

  ngOnInit() {
  }

  onPage(name: string) {
    this.util.navigateToPage(name);
  }

  onSalonList(name: string) {
    const param: NavigationExtras = {
      queryParams: {
        name: name
      }
    };
    this.util.navigateToPage('services-list', param);
  }

  onInfo(index: any) {
    const param: NavigationExtras = {
      queryParams: {
        id: index
      }
    };
    this.util.navigateToPage('salon-info', param);
  }

}
