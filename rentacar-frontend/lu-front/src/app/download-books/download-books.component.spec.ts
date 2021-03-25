import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DownloadBooksComponent } from './download-books.component';

describe('DownloadBooksComponent', () => {
  let component: DownloadBooksComponent;
  let fixture: ComponentFixture<DownloadBooksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DownloadBooksComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DownloadBooksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
