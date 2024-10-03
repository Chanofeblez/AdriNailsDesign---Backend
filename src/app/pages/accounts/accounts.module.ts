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

import { AccountsPageRoutingModule } from './accounts-routing.module';

import { AccountsPage } from './accounts.page';
import { ComponentModule } from 'src/app/components/component.module';
@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    AccountsPageRoutingModule,
    ComponentModule
  ],
  declarations: [AccountsPage]
})
export class AccountsPageModule { }
