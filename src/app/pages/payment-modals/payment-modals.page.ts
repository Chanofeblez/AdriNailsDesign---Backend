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
  selector: 'app-payment-modals',
  templateUrl: './payment-modals.page.html',
  styleUrls: ['./payment-modals.page.scss'],
})
export class PaymentModalsPage implements OnInit {
  selected: any = 'paypal';
  constructor(
    private modalController: ModalController
  ) { }

  ngOnInit() {
  }

  close(action: string) {
    this.modalController.dismiss(action, action);
  }

}
