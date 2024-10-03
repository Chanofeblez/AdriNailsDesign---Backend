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

import { NearSalonPageRoutingModule } from './near-salon-routing.module';

import { NearSalonPage } from './near-salon.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    NearSalonPageRoutingModule
  ],
  declarations: [NearSalonPage]
})
export class NearSalonPageModule { }
