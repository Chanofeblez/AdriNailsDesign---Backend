/*
  Authors : initappz (Rahul Jograna)
  Website : https://initappz.com/
  App Name : Salon - 2 This App Template Source code is licensed as per the
  terms found in the Website https://initappz.com/license
  Copyright and Good Faith Purchasers © 2023-present initappz.
*/
import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { UtilService } from 'src/app/services/util.service';
import { PaymentModalsPage } from '../payment-modals/payment-modals.page';

@Component({
  selector: 'app-payment-summary',
  templateUrl: './payment-summary.page.html',
  styleUrls: ['./payment-summary.page.scss'],
})
export class PaymentSummaryPage implements OnInit {

  constructor(
    public util: UtilService,
    private modalController: ModalController
  ) { }

  ngOnInit() {
  }

  onBack() {
    this.util.onBack();
  }

  async openPaymentModal() {
    const modal = await this.modalController.create({
      component: PaymentModalsPage,
      cssClass: 'payment-modal'
    });
    modal.onDidDismiss().then((data) => {
      console.log(data);
      if (data && data.data && data.data == 'ok') {
        this.util.navigateToPage('success-payments');
      }
    });
    await modal.present();
  }

}
