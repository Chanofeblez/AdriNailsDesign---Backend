/*
  Authors : initappz (Rahul Jograna)
  Website : https://initappz.com/
  App Name : Salon - 2 This App Template Source code is licensed as per the
  terms found in the Website https://initappz.com/license
  Copyright and Good Faith Purchasers © 2023-present initappz.
*/
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NearSalonPage } from './near-salon.page';

describe('NearSalonPage', () => {
  let component: NearSalonPage;
  let fixture: ComponentFixture<NearSalonPage>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(NearSalonPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
