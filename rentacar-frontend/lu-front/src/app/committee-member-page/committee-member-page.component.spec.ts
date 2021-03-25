import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommitteeMemberPageComponent } from './committee-member-page.component';

describe('CommitteeMemberPageComponent', () => {
  let component: CommitteeMemberPageComponent;
  let fixture: ComponentFixture<CommitteeMemberPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CommitteeMemberPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CommitteeMemberPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
