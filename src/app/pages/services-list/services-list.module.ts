/*
  Authors : initappz (Rahul Jograna)
  Website : https://initappz.com/
  App Name : Salon - 2 This App Template Source code is licensed as per the
  terms found in the Website https://initappz.com/license
  Copyright and Good Faith Purchasers © 2023-present initappz.
*/
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ServicesListPageRoutingModule } from './services-list-routing.module';

import { ServicesListPage } from './services-list.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ServicesListPageRoutingModule
  ],
  declarations: [ServicesListPage]
})
export class ServicesListPageModule { }
