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
  selector: 'app-select-gender',
  templateUrl: './select-gender.page.html',
  styleUrls: ['./select-gender.page.scss'],
})
export class SelectGenderPage implements OnInit {
  selected: any = 'man';
  constructor(
    public util: UtilService
  ) { }

  ngOnInit() {
  }

  changeGender(name: string) {
    this.selected = name;
  }

  onNext() {
    console.log('next');
    this.util.navigateRoot('login');
  }

}
