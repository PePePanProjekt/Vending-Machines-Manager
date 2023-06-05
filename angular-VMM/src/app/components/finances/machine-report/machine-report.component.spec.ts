import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MachineReportComponent } from './machine-report.component';

describe('MachineReportComponent', () => {
  let component: MachineReportComponent;
  let fixture: ComponentFixture<MachineReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MachineReportComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MachineReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
