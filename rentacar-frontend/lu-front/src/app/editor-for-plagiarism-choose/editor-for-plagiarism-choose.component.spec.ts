import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditorForPlagiarismChooseComponent } from './editor-for-plagiarism-choose.component';

describe('EditorForPlagiarismChooseComponent', () => {
  let component: EditorForPlagiarismChooseComponent;
  let fixture: ComponentFixture<EditorForPlagiarismChooseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditorForPlagiarismChooseComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditorForPlagiarismChooseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
