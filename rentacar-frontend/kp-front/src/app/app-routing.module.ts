import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './home-page/home-page.component';
import { SuccessfulPaymentComponent } from './successful-payment/successful-payment.component';
import { LoginComponent } from './login/login.component';
import { BankLoginComponent } from './bank-login/bank-login.component';
import { SubscriptionFormComponent } from './subscription-form/subscription-form.component';

const routes: Routes = [
  { path: 'payment/:id', component: HomePageComponent },
  { path: '', component: HomePageComponent },
  { path: 'success', component: SuccessfulPaymentComponent },
  { path: 'login', component: LoginComponent },
  { path: 'bank', component: BankLoginComponent },
  {path : 'subscription-form/:id', component: SubscriptionFormComponent}
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ],
  declarations: []
})
export class AppRoutingModule { }