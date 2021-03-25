import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeclineModalComponent } from './decline-modal.component';

describe('DeclineModalComponent', () => {
  let component: DeclineModalComponent;
  let fixture: ComponentFixture<DeclineModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeclineModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DeclineModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
