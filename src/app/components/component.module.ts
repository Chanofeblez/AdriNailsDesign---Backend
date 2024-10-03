/*
  Authors : initappz (Rahul Jograna)
  Website : https://initappz.com/
  App Name : Salon - 2 This App Template Source code is licensed as per the
  terms found in the Website https://initappz.com/license
  Copyright and Good Faith Purchasers © 2023-present initappz.
*/
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { TabsComponent } from './tabs/tabs.component';
import { HeaderComponent } from './header/header.component';

const components = [
  TabsComponent,
  HeaderComponent
];
@NgModule({
  declarations: [
    components
  ],
  imports: [
    CommonModule,
    IonicModule,
  ],
  exports: [
    ...components,
  ]
})
export class ComponentModule { }
