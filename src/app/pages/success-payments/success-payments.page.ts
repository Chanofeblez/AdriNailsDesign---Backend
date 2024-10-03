/*
  Authors : initappz (Rahul Jograna)
  Website : https://initappz.com/
  App Name : Salon - 2 This App Template Source code is licensed as per the
  terms found in the Website https://initappz.com/license
  Copyright and Good Faith Purchasers © 2023-present initappz.
*/
import { Component, OnInit } from '@angular/core';
import { UtilService } from 'src/app/services/util.service';

@Component({
  selector: 'app-success-payments',
  templateUrl: './success-payments.page.html',
  styleUrls: ['./success-payments.page.scss'],
})
export class SuccessPaymentsPage implements OnInit {

  constructor(
    public util: UtilService
  ) { }

  ngOnInit() {
  }

  onHome() {
    this.util.navigateRoot('/home');
  }

  onList() {
    this.util.navigateRoot('/appointments');
  }

}
