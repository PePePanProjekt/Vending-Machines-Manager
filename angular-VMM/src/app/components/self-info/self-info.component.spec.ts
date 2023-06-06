import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelfInfoComponent } from './self-info.component';

describe('SelfInfoComponent', () => {
  let component: SelfInfoComponent;
  let fixture: ComponentFixture<SelfInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SelfInfoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SelfInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
