import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadPdfsComponent } from './upload-pdfs.component';

describe('UploadPdfsComponent', () => {
  let component: UploadPdfsComponent;
  let fixture: ComponentFixture<UploadPdfsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UploadPdfsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UploadPdfsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
