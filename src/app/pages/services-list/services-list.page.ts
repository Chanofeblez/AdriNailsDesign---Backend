/*
  Authors : initappz (Rahul Jograna)
  Website : https://initappz.com/
  App Name : Salon - 2 This App Template Source code is licensed as per the
  terms found in the Website https://initappz.com/license
  Copyright and Good Faith Purchasers © 2023-present initappz.
*/
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationExtras } from '@angular/router';
import { UtilService } from 'src/app/services/util.service';

@Component({
  selector: 'app-services-list',
  templateUrl: './services-list.page.html',
  styleUrls: ['./services-list.page.scss'],
})
export class ServicesListPage implements OnInit {
  name: any = '';
  constructor(
    public util: UtilService,
    private route: ActivatedRoute
  ) {
    this.route.queryParams.subscribe((data: any) => {
      this.name = data.name;
    });
  }

  ngOnInit() {
  }

  onBack() {
    this.util.onBack();
  }

  onInfo(index: any) {
    const param: NavigationExtras = {
      queryParams: {
        id: index
      }
    };
    this.util.navigateToPage('salon-info', param);
  }

}
