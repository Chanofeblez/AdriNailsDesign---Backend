/*
  Authors : initappz (Rahul Jograna)
  Website : https://initappz.com/
  App Name : Salon - 2 This App Template Source code is licensed as per the
  terms found in the Website https://initappz.com/license
  Copyright and Good Faith Purchasers © 2023-present initappz.
*/
import { Component, inject, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UtilService } from 'src/app/services/util.service';

@Component({
  selector: 'app-tabs',
  templateUrl: './tabs.component.html',
  styleUrls: ['./tabs.component.scss'],
})
export class TabsComponent implements OnInit {

  @Input() navigation: string;

  private router = inject(Router);
  public util = inject(UtilService);

  constructor() { }

  ngOnInit() {
    console.log(this.navigation);
  }

  onNavigate(name: string) {
    this.navigation = name;  // Actualiza el valor de navigation
    this.router.navigate([`/${name}`]);  // Usa el router para navegar a la ruta correspondiente
  }
}
