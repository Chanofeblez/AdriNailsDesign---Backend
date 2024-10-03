/*
  Authors : initappz (Rahul Jograna)
  Website : https://initappz.com/
  App Name : Salon - 2 This App Template Source code is licensed as per the
  terms found in the Website https://initappz.com/license
  Copyright and Good Faith Purchasers © 2023-present initappz.
*/
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ServicesListPage } from './services-list.page';

describe('ServicesListPage', () => {
  let component: ServicesListPage;
  let fixture: ComponentFixture<ServicesListPage>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(ServicesListPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
