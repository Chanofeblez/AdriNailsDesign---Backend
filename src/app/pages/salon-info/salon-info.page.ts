/*
  Authors : initappz (Rahul Jograna)
  Website : https://initappz.com/
  App Name : Salon - 2 This App Template Source code is licensed as per the
  terms found in the Website https://initappz.com/license
  Copyright and Good Faith Purchasers © 2023-present initappz.
*/
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { UtilService } from 'src/app/services/util.service';
import { ActivatedRoute } from '@angular/router';
import { register } from 'swiper/element';
import Swiper from 'swiper';

register();
@Component({
  selector: 'app-salon-info',
  templateUrl: './salon-info.page.html',
  styleUrls: ['./salon-info.page.scss'],
})
export class SalonInfoPage implements OnInit {
  @ViewChild("swiper") swiper?: ElementRef<{ swiper: Swiper }>
  name: any = '';
  address: any = '';
  rate: any = '';
  activeIndex: any = 0;
  slideOptStores = {
    initialSlide: 0,
    slidesPerView: 4.2,
  };
  activeContent: any = 'about';

  activeService: any = '';

  portfolioContent: any = 'photo';
  constructor(
    public util: UtilService,
    private route: ActivatedRoute
  ) {
    this.route.queryParams.subscribe((data: any) => {
      console.log(data);
      const info = this.util.salonList[data.id];
      this.name = info.name;
      this.address = info.address;
      this.rate = info.rate;
      this.activeService = this.util.categories[0].name;
    });
  }

  ngOnInit() {
  }

  onBack() {
    this.util.onBack();
  }
  changed() {
    this.activeIndex = this.swiper?.nativeElement.swiper.activeIndex;
  }

  changeServices(name: any) {
    this.activeService = name;
  }

  changePortfolio(name: string) {
    this.portfolioContent = name;
  }

  onSlot() {
    this.util.navigateToPage('select-slot');
  }

}
