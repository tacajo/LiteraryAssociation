import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PublishingABookComponent } from './publishing-abook.component';

describe('PublishingABookComponent', () => {
  let component: PublishingABookComponent;
  let fixture: ComponentFixture<PublishingABookComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PublishingABookComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PublishingABookComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
