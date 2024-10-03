/*
  Authors : initappz (Rahul Jograna)
  Website : https://initappz.com/
  App Name : Salon - 2 This App Template Source code is licensed as per the
  terms found in the Website https://initappz.com/license
  Copyright and Good Faith Purchasers © 2023-present initappz.
*/
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SalonInfoPage } from './salon-info.page';

describe('SalonInfoPage', () => {
  let component: SalonInfoPage;
  let fixture: ComponentFixture<SalonInfoPage>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(SalonInfoPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
