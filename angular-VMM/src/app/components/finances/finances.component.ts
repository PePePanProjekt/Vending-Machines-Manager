import {Component} from '@angular/core';
import {DatePipe} from "@angular/common";
import {FinanceService} from "../../services/finance.service";
import {AllStats} from "../../models/Finance/AllStats";
import {MatSnackBar} from "@angular/material/snack-bar";
import * as XLSX from 'xlsx';

@Component({
    selector: 'app-finances',
    templateUrl: './finances.component.html',
    styleUrls: ['./finances.component.css']
})
export class FinancesComponent {
    toDate = new Date();
    sinceDate = new Date(this.toDate.getFullYear(), this.toDate.getMonth(), 1, 0, 0, 0, 0);
    allStats?: AllStats;

    constructor(
        private financeService: FinanceService,
        private snackBar: MatSnackBar
    ) {
    }

    ngOnInit() {
        this.getReport();
    }



    private getReport() {
        this.financeService.getAllStats(this.sinceDate, this.toDate).subscribe(report => {
            this.allStats = report;
        })
    }

    changeDates(newSince: string, newTo: string) {
        if(newSince == '' || newTo == ''){
            this.snackBar.open(
                `Please select a dates`
                ,"OK")
            return;
        }

        this.sinceDate = new Date(newSince);
        this.toDate = new Date(newTo);
        this.getReport();
    }

    downloadReport(): void {
        const transformedData = this.transformData();
        const worksheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(transformedData);
        const workbook: XLSX.WorkBook = { Sheets: { 'data': worksheet }, SheetNames: ['data'] };
        const excelBuffer: any = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });
        this.saveAsExcelFile(excelBuffer, 'report');
    }

    transformData(): any[] {
        if(this.allStats != null) {
            const transformedData = [];
            transformedData.push({Field: 'Since', Value: this.sinceDate});
            transformedData.push({Field: 'To', Value: this.toDate});
            transformedData.push({Field: 'Balance', Value: this.allStats.totalProfit-this.allStats.totalExpenses});
            transformedData.push({Field: 'Total Sales', Value: this.allStats.totalSales});
            transformedData.push({Field: 'Total Profit', Value: this.allStats.totalProfit});
            transformedData.push({Field: 'Total Bought', Value: this.allStats.totalBought});
            transformedData.push({Field: 'Total Expenses', Value: this.allStats.totalExpenses});
            transformedData.push({Field: 'Best Selling Item ID', Value: this.allStats.bestSellingItemId});
            transformedData.push({Field: 'Best Selling Item Name', Value: this.allStats.bestSellingItemName});
            transformedData.push({Field: 'Best Selling Item Amount', Value: this.allStats.bestSellingItemAmount});
            transformedData.push({Field: 'Worst Selling Item ID', Value: this.allStats.worstSellingItemId});
            transformedData.push({Field: 'Worst Selling Item Name', Value: this.allStats.worstSellingItemName});
            transformedData.push({Field: 'Worst Selling Item Amount', Value: this.allStats.worstSellingItemAmount});
            transformedData.push({Field: 'Best Performing Machine ID', Value: this.allStats.bestPerformingMachineId});
            transformedData.push({
                Field: 'Best Performing Machine Name',
                Value: this.allStats.bestPerformingMachineName
            });
            transformedData.push({
                Field: 'Best Performing Machine Sales',
                Value: this.allStats.bestPerformingMachineSales
            });
            transformedData.push({Field: 'Worst Performing Machine ID', Value: this.allStats.worstPerformingMachineId});
            transformedData.push({
                Field: 'Worst Performing Machine Name',
                Value: this.allStats.worstPerformingMachineName
            });
            transformedData.push({
                Field: 'Worst Performing Machine Sales',
                Value: this.allStats.worstPerformingMachineSales
            });
            return transformedData;
        }
        return [];
    }

    saveAsExcelFile(buffer: any, fileName: string): void {
        const data: Blob = new Blob([buffer], {type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'});
        const a: HTMLAnchorElement = document.createElement('a');
        const url: string = window.URL.createObjectURL(data);
        a.href = url;
        a.download = fileName + '.xlsx';
        a.click();
        window.URL.revokeObjectURL(url);
        a.remove();
    }
}
