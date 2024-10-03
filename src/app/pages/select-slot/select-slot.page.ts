/*
  Authors : initappz (Rahul Jograna)
  Website : https://initappz.com/
  App Name : Salon - 2 This App Template Source code is licensed as per the
  terms found in the Website https://initappz.com/license
  Copyright and Good Faith Purchasers © 2023-present initappz.
*/
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { UtilService } from 'src/app/services/util.service';
import * as moment from 'moment';
import { register } from 'swiper/element';
import Swiper from 'swiper';

register();
@Component({
  selector: 'app-select-slot',
  templateUrl: './select-slot.page.html',
  styleUrls: ['./select-slot.page.scss'],
})
export class SelectSlotPage implements OnInit {
  @ViewChild("swiper") swiper?: ElementRef<{ swiper: Swiper }>
  currentDate: any;
  currentWeek: any = 0;
  days: any[] = [];
  initDate: any;
  selectedDate: any;
  time: any[] = ['09:00 AM', '09:30 AM', '10:00 AM', '10:30 AM',
    '11:00 AM', '11:30 AM', '12:00 PM', '12:30 PM', '01:00 PM', '01:30 PM', '03:00 PM', '04:00 PM',
    '04:30 PM', '05:00 PM', '05:30 PM', '06:30 PM', '07:00 PM', '07:30 PM', '08:00 PM', '08:30 PM'
  ];
  selectedSlot: any = '';
  constructor(
    public util: UtilService
  ) {
    setTimeout(() => {
      this.dateConfi();
    }, 1000);
  }

  ngOnInit() {
  }

  onBack() {
    this.util.onBack();
  }

  dateConfi() {
    this.selectedDate = moment().format().split('T')[0];
    this.currentDate = moment().format().split('T')[0];
    this.days = [];
    this.initDate = moment().format();
    const startOfWeek = moment().startOf('week');
    const endOfWeek = moment().endOf('week');
    let day = startOfWeek;
    while (day <= endOfWeek) {
      const data = {
        date: day.toDate().getDate(),
        day: day.format('ddd'),
        val: day.format(),
        selectDay: day.format('dddd DD MMMM')
      }
      this.days.push(data);
      day = day.clone().add(1, 'd');
    }
  }

  previousWeek() {
    if (this.currentWeek !== 0) {
      this.days = [];
      this.currentWeek = this.currentWeek - 1;
      const startOfWeek = moment().add(this.currentWeek, 'weeks').startOf('week');
      const endOfWeek = moment().add(this.currentWeek, 'weeks').endOf('week');
      let day = startOfWeek;
      while (day <= endOfWeek) {
        const data = {
          date: day.toDate().getDate(),
          day: day.format('ddd'),
          val: day.format(),
          selectDay: day.format('dddd DD MMMM')
        }
        this.days.push(data);
        day = day.clone().add(1, 'd');
      }
      this.currentDate = this.days[0].val;
      console.log(this.days);
    }
  }

  selectDate(date: any) {
    console.log(date)
    if (this.currentDate <= date.val.split('T')[0]) {
      this.selectedDate = date.val.split('T')[0];
      console.log(this.selectedDate);
    }
  }

  nextWeek() {
    this.days = [];
    this.currentWeek++;
    console.log(this.currentWeek);
    const startOfWeek = moment().add(this.currentWeek, 'weeks').startOf('week');
    const endOfWeek = moment().add(this.currentWeek, 'weeks').endOf('week');
    let day = startOfWeek;
    while (day <= endOfWeek) {
      const data = {
        date: day.toDate().getDate(),
        day: day.format('ddd'),
        val: day.format(),
        selectDay: day.format('dddd DD MMMM')
      }
      this.days.push(data);
      day = day.clone().add(1, 'd');
    }
    this.currentDate = this.days[0].val;
    console.log(this.days);
  }

  getMonth() {
    return moment(this.currentDate).format('MMMM YYYY');
  }

  changeSlot(name: string, index: number) {
    if (index % 2 != 0) {
      this.selectedSlot = name;
    }
  }

  onSummary() {
    this.util.navigateToPage('payment-summary');
  }

}
