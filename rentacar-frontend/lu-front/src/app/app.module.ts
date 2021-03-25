import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegistrationComponent } from './registration/registration.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ConfirmAccountComponent } from './confirm-account/confirm-account.component';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { MatTabsModule } from '@angular/material/tabs';
import { TokenInterceptor } from './interceptor/TokenInterceptor';
import { UserService } from './service/user.service';
import { HomeComponent } from './home/home.component';
import { MatIconModule } from '@angular/material/icon';
import { MatRadioModule } from '@angular/material/radio';
import { PaymentOptionComponent } from './payment-option/payment-option.component';
import { SuccessComponent } from './success/success.component';
import { FailedComponent } from './failed/failed.component';
import { CommitteeMemberPageComponent } from './committee-member-page/committee-member-page.component';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { PlagiarismComponent } from './plagiarism/plagiarism.component';
import { PublishingABookComponent } from './publishing-abook/publishing-abook.component';
import { EditorTasksComponent } from './editor-tasks/editor-tasks.component';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { DeclineModalComponent } from './decline-modal/decline-modal.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { UploadPdfsComponent } from './upload-pdfs/upload-pdfs.component';
import { EditorForPlagiarismChooseComponent } from './editor-for-plagiarism-choose/editor-for-plagiarism-choose.component';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { FormComponent } from './form/form.component';
import { OriginalsComponent } from './originals/originals.component';
import { ChooseOriginalComponent } from './choose-original/choose-original.component';
import { DownloadBooksComponent } from './download-books/download-books.component';
import { MatTooltipModule } from '@angular/material/tooltip';

@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    ConfirmAccountComponent,
    HomeComponent,
    PaymentOptionComponent,
    SuccessComponent,
    FailedComponent,
    CommitteeMemberPageComponent,
    PlagiarismComponent,
    PublishingABookComponent,
    EditorTasksComponent,
    DeclineModalComponent,
    UploadPdfsComponent,
    EditorForPlagiarismChooseComponent,
    FormComponent,
    OriginalsComponent,
    ChooseOriginalComponent,
    DownloadBooksComponent
  ],
  entryComponents: [DeclineModalComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    MatInputModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatTabsModule,
    MatIconModule,
    MatRadioModule,
    ReactiveFormsModule,
    MatSelectModule,
    MatFormFieldModule,
    MatSnackBarModule,
    MatProgressBarModule,
    MatCheckboxModule,
    NgbModule,
    NgMultiSelectDropDownModule.forRoot(),
    MatProgressBarModule,
    MatTooltipModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    }, UserService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
