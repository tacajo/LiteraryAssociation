import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CommitteeMemberPageComponent } from './committee-member-page/committee-member-page.component';
import { ConfirmAccountComponent } from './confirm-account/confirm-account.component';
import { FailedComponent } from './failed/failed.component';
import { HomeComponent } from './home/home.component';
import { RegistrationComponent } from './registration/registration.component';
import { PlagiarismComponent } from './plagiarism/plagiarism.component';
import { SuccessComponent } from './success/success.component';
import { EditorForPlagiarismChooseComponent } from './editor-for-plagiarism-choose/editor-for-plagiarism-choose.component';

const routes: Routes = [
  { path: '', component: RegistrationComponent },
  { path: 'confirm-account', component: ConfirmAccountComponent }, 
  { path: 'home', component: HomeComponent},
  { path: 'success', component: SuccessComponent},
  { path: 'failed', component: FailedComponent},
  { path: 'committee-member', component: CommitteeMemberPageComponent},
  { path: 'plagiarism', component: PlagiarismComponent},
  { path: 'pick-editors', component: EditorForPlagiarismChooseComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
