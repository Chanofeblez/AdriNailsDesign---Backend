/*
  Authors : initappz (Rahul Jograna)
  Website : https://initappz.com/
  App Name : Salon - 2 This App Template Source code is licensed as per the
  terms found in the Website https://initappz.com/license
  Copyright and Good Faith Purchasers © 2023-present initappz.
*/
import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';

@Component({
  selector: 'app-write-reviews',
  templateUrl: './write-reviews.page.html',
  styleUrls: ['./write-reviews.page.scss'],
})
export class WriteReviewsPage implements OnInit {
  rate: any = 2;
  constructor(
    private modalController: ModalController
  ) { }

  ngOnInit() {
  }

  changeRate(num: number) {
    this.rate = num;
  }

  close() {
    this.modalController.dismiss();
  }

}
