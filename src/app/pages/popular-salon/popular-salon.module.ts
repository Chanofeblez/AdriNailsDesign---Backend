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

import { PopularSalonPageRoutingModule } from './popular-salon-routing.module';

import { PopularSalonPage } from './popular-salon.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    PopularSalonPageRoutingModule
  ],
  declarations: [PopularSalonPage]
})
export class PopularSalonPageModule { }
