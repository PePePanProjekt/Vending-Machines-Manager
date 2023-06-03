export interface AllStats {
    totalSales: number;
    totalProfit: number;
    totalBought: number;
    totalExpenses: number;
    // best / worst items
    bestSellingItemId: string;
    bestSellingItemName: string;
    bestSellingItemAmount: number;
    worstSellingItemId: string;
    worstSellingItemName: string;
    worstSellingItemAmount: number;
    // best / worst machines
    bestPerformingMachineId: string;
    bestPerformingMachineName: string;
    bestPerformingMachineSales: number;
    worstPerformingMachineId: string;
    worstPerformingMachineName: string;
    worstPerformingMachineSales: number;
}
