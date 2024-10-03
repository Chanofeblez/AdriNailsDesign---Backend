/*
  Authors : initappz (Rahul Jograna)
  Website : https://initappz.com/
  App Name : Salon - 2 This App Template Source code is licensed as per the
  terms found in the Website https://initappz.com/license
  Copyright and Good Faith Purchasers © 2023-present initappz.
*/
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { WriteReviewsPage } from './write-reviews.page';

describe('WriteReviewsPage', () => {
  let component: WriteReviewsPage;
  let fixture: ComponentFixture<WriteReviewsPage>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(WriteReviewsPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
