import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NearestHospitalDetailsComponent } from './nearest-hospital-details.component';

describe('NearestHospitalDetailsComponent', () => {
  let component: NearestHospitalDetailsComponent;
  let fixture: ComponentFixture<NearestHospitalDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NearestHospitalDetailsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NearestHospitalDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
