import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CustomerInfoPage } from './customer-info.page';

describe('CustomerInfoPage', () => {
  let component: CustomerInfoPage;
  let fixture: ComponentFixture<CustomerInfoPage>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(CustomerInfoPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
