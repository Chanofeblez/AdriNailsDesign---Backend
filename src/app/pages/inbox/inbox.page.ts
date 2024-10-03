/*
  Authors : initappz (Rahul Jograna)
  Website : https://initappz.com/
  App Name : Salon - 2 This App Template Source code is licensed as per the
  terms found in the Website https://initappz.com/license
  Copyright and Good Faith Purchasers © 2023-present initappz.
*/
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UtilService } from 'src/app/services/util.service';

@Component({
  selector: 'app-inbox',
  templateUrl: './inbox.page.html',
  styleUrls: ['./inbox.page.scss'],
})
export class InboxPage implements OnInit {
  name: any = '';
  constructor(
    public util: UtilService,
    private route: ActivatedRoute
  ) {
    this.route.queryParams.subscribe((data: any) => {
      this.name = this.util.userList[data.id].name;
    })
  }

  ngOnInit() {
  }

  onBack() {
    this.util.onBack();
  }

}
