import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChooseOriginalComponent } from './choose-original.component';

describe('ChooseOriginalComponent', () => {
  let component: ChooseOriginalComponent;
  let fixture: ComponentFixture<ChooseOriginalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChooseOriginalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChooseOriginalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
