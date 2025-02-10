let categoryField = document.getElementById("category");
let dateField = document.getElementById("date");
let amountField = document.getElementById("amount");
let expenseSum = 0;

async function sendValue(selectedExpenseCategory) {
    try {
        const response = await fetch('/api/v1/menu/expense/selectedExpenseCategory', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ option: selectedExpenseCategory })
        });
        const data = await response.json();
        expenseSum = data.expenseSum;
        return expenseSum;
    } catch (error) {
        console.error('Error:', error);
    }
}

categoryField.addEventListener('change', async () => {

    const selectedOption = categoryField.options[categoryField.selectedIndex];
    const category = selectedOption.value;
    const date = dateField.value;
    const amount = amountField.value;
    const limitAmount = selectedOption.getAttribute('limitAmount');
    await sendValue(category);

    if (!category) {
        hideBalance();
    }

    checkExpenseBalance(category, date, amount, limitAmount, expenseSum);
})

dateField.addEventListener('change', async () => {

    const selectedOption = categoryField.options[categoryField.selectedIndex];
        const category = selectedOption.value;
        const date = dateField.value;
        const amount = amountField.value;
        const limitAmount = selectedOption.getAttribute('limitAmount');
        await sendValue(category);

        if (!category) {
            hideBalance();
        }

        checkExpenseBalance(category, date, amount, limitAmount, expenseSum);
})

amountField.addEventListener('change', async () => {

    const selectedOption = categoryField.options[categoryField.selectedIndex];
        const category = selectedOption.value;
        const date = dateField.value;
        const amount = amountField.value;
        const limitAmount = selectedOption.getAttribute('limitAmount');
        await sendValue(category);

        if (!category) {
            hideBalance();
        }

        checkExpenseBalance(category, date, amount, limitAmount, expenseSum);
})

const renderOnDOM = async (intLimitAmount, intSumOfExpenses, differenceLimitSumOfExpenses, sumProvidedAmountExpenses) => {

    document.getElementById("box").style.visibility = "visible";

    document.getElementById("limit").innerHTML = ` ${intLimitAmount}$`;
    document.getElementById("expenses").innerHTML = ` ${intSumOfExpenses}$`;
    document.getElementById("diff").innerHTML = ` ${differenceLimitSumOfExpenses}$`;
    document.getElementById("sum").innerHTML = ` ${sumProvidedAmountExpenses}$`

    if (intLimitAmount >= sumProvidedAmountExpenses) {
        document.getElementById("box").style.color = " #85bb65";
    }
    else if (intLimitAmount < sumProvidedAmountExpenses) {
        document.getElementById("box").style.color = "rgb(241, 82, 82)";
    }
}

const displayExpenseBalance = async (amount, expenseSum, limitAmount) => {

    var intLimitAmount = parseFloat(limitAmount);
    if (expenseSum === null) {
        expenseSum = 0;
    }
    console.log(expenseSum);
    var intSumOfExpenses = parseFloat(expenseSum);
    var intAmount = parseFloat(amount);

    var differenceLimitSumOfExpenses = intLimitAmount - intSumOfExpenses;
    var sumProvidedAmountExpenses = intAmount + intSumOfExpenses;

    renderOnDOM(intLimitAmount, intSumOfExpenses, differenceLimitSumOfExpenses, sumProvidedAmountExpenses);
}

const checkExpenseBalance = async (category, date, amount, limitAmount, expenseSum) => {

    if (!!category) {
        if (limitAmount == null) {
            hideBalance();
        }
        if (!!date) {
            if (!!amount && !!limitAmount) {
                displayExpenseBalance(amount, expenseSum, limitAmount);
            }
        }
    }
}

const hideBalance = () => {
    document.getElementById("box").style.visibility = "hidden";
}