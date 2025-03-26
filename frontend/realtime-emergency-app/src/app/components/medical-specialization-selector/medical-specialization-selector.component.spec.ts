import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedicalSpecializationSelectorComponent } from './medical-specialization-selector.component';

describe('MedicalSpecializationSelectorComponent', () => {
  let component: MedicalSpecializationSelectorComponent;
  let fixture: ComponentFixture<MedicalSpecializationSelectorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MedicalSpecializationSelectorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MedicalSpecializationSelectorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
