import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SaleApiComponent } from './sale-api.component';

describe('SaleApiComponent', () => {
  let component: SaleApiComponent;
  let fixture: ComponentFixture<SaleApiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SaleApiComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SaleApiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
