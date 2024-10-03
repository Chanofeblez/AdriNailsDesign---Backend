/*
  Authors : initappz (Rahul Jograna)
  Website : https://initappz.com/
  App Name : Salon - 2 This App Template Source code is licensed as per the
  terms found in the Website https://initappz.com/license
  Copyright and Good Faith Purchasers © 2023-present initappz.
*/
import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'appointments',
    pathMatch: 'full'
  },
  {
    path: 'accounts',
    loadChildren: () => import('./pages/accounts/accounts.module').then(m => m.AccountsPageModule)
  },
  {
    path: 'appointments',
    loadChildren: () => import('./pages/appointments/appointments.module').then(m => m.AppointmentsPageModule)
  },
  {
    path: 'booking-info/:id',
    loadChildren: () => import('./pages/booking-info/booking-info.module').then(m => m.BookingInfoPageModule)
  },
  {
    path: 'chats',
    loadChildren: () => import('./pages/chats/chats.module').then(m => m.ChatsPageModule)
  },
  {
    path: 'forgot-password',
    loadChildren: () => import('./pages/forgot-password/forgot-password.module').then(m => m.ForgotPasswordPageModule)
  },
  {
    path: 'home',
    loadChildren: () => import('./pages/home/home.module').then(m => m.HomePageModule)
  },
  {
    path: 'inbox',
    loadChildren: () => import('./pages/inbox/inbox.module').then(m => m.InboxPageModule)
  },
  {
    path: 'location',
    loadChildren: () => import('./pages/location/location.module').then(m => m.LocationPageModule)
  },
  {
    path: 'login',
    loadChildren: () => import('./pages/login/login.module').then(m => m.LoginPageModule)
  },
  {
    path: 'near-by',
    loadChildren: () => import('./pages/near-by/near-by.module').then(m => m.NearByPageModule)
  },
  {
    path: 'near-salon',
    loadChildren: () => import('./pages/near-salon/near-salon.module').then(m => m.NearSalonPageModule)
  },
  {
    path: 'payment-modals',
    loadChildren: () => import('./pages/payment-modals/payment-modals.module').then(m => m.PaymentModalsPageModule)
  },
  {
    path: 'payment-summary',
    loadChildren: () => import('./pages/payment-summary/payment-summary.module').then(m => m.PaymentSummaryPageModule)
  },
  {
    path: 'popular-salon',
    loadChildren: () => import('./pages/popular-salon/popular-salon.module').then(m => m.PopularSalonPageModule)
  },
  {
    path: 'register',
    loadChildren: () => import('./pages/register/register.module').then(m => m.RegisterPageModule)
  },
  {
    path: 'salon-info',
    loadChildren: () => import('./pages/salon-info/salon-info.module').then(m => m.SalonInfoPageModule)
  },
  {
    path: 'search-location',
    loadChildren: () => import('./pages/search-location/search-location.module').then(m => m.SearchLocationPageModule)
  },
  {
    path: 'select-gender',
    loadChildren: () => import('./pages/select-gender/select-gender.module').then(m => m.SelectGenderPageModule)
  },
  {
    path: 'select-slot',
    loadChildren: () => import('./pages/select-slot/select-slot.module').then(m => m.SelectSlotPageModule)
  },
  {
    path: 'services',
    loadChildren: () => import('./pages/services/services.module').then(m => m.ServicesPageModule)
  },
  {
    path: 'services-list',
    loadChildren: () => import('./pages/services-list/services-list.module').then(m => m.ServicesListPageModule)
  },
  {
    path: 'success-payments',
    loadChildren: () => import('./pages/success-payments/success-payments.module').then(m => m.SuccessPaymentsPageModule)
  },
  {
    path: 'verify',
    loadChildren: () => import('./pages/verify/verify.module').then(m => m.VerifyPageModule)
  },
  {
    path: 'welcome',
    loadChildren: () => import('./pages/welcome/welcome.module').then(m => m.WelcomePageModule)
  },
  {
    path: 'write-reviews',
    loadChildren: () => import('./pages/write-reviews/write-reviews.module').then(m => m.WriteReviewsPageModule)
  },
  {
    path: 'gallery',
    loadChildren: () => import('./pages/gallery/gallery.module').then( m => m.GalleryPageModule)
  },
  {
    path: 'course',
    loadChildren: () => import('./pages/course/course.module').then( m => m.CoursePageModule)
  },
  {
    path: 'customer-info/:id/:email',
    loadChildren: () => import('./pages/customer-info/customer-info.module').then( m => m.CustomerInfoPageModule)
  },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
