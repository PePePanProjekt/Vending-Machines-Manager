import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddMachineComponent } from './add-machine.component';

describe('AddMachineComponent', () => {
  let component: AddMachineComponent;
  let fixture: ComponentFixture<AddMachineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddMachineComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddMachineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
