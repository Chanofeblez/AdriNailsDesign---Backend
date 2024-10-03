/*
  Authors : initappz (Rahul Jograna)
  Website : https://initappz.com/
  App Name : Salon - 2 This App Template Source code is licensed as per the
  terms found in the Website https://initappz.com/license
  Copyright and Good Faith Purchasers © 2023-present initappz.
*/
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { UtilService } from 'src/app/services/util.service';
import { register } from 'swiper/element';
import Swiper from 'swiper';

register();
@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.page.html',
  styleUrls: ['./welcome.page.scss'],
})
export class WelcomePage implements OnInit {
  @ViewChild("swiper") swiper?: ElementRef<{ swiper: Swiper }>
  activeIndex: any = 0;
  constructor(
    public util: UtilService
  ) { }

  ngOnInit() {
  }

  changed() {
    this.activeIndex = this.swiper?.nativeElement.swiper.activeIndex;
  }

  onNext() {
    console.log('next');
    if (this.swiper?.nativeElement.swiper.isEnd == true) {
      console.log('gender page');
      this.util.navigateRoot('/select-gender');
    } else {
      this.swiper?.nativeElement.swiper.slideNext();
    }
  }

  onSkip() {

  }

}
