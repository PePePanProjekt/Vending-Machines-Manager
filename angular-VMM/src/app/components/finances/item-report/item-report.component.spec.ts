import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ItemReportComponent } from './item-report.component';

describe('ItemReportComponent', () => {
  let component: ItemReportComponent;
  let fixture: ComponentFixture<ItemReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ItemReportComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ItemReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
