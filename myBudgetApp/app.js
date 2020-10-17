// a backend controller wich controlls the  saving of the items
let budgetCtr = (function(){
    let db = {
        income : 0,
        expense : 0,
        budget : 0,
        percentage: -1,
    
        expCont : [],
        incCont : [],

        updateBuget : function(){
            this.budget = this.income - this.expense;
        },
        updatePerc : function(){
            if(this.income > 0){
                this.percentage = Math.round((this.expense / this.income) * 100);
                }
            }
        }
    

    let incItem = function(ID, val, desc){
        this.ID = ID;
        this.val = val;
        this.desc = desc;
    }

    let expItem = function(ID, val, desc){
        this.ID = ID;
        this.val = val;
        this.desc = desc;
        this.percentage = -1;
        this.calcPerc = function(){
            if(db.income > 0){
                this.percentage = Math.round((this.val/ db.income) * 100);
                }
            else{
                this.percentage = -1;
            }
        };
    }

    let updatePerc = function(){
        db.expCont.forEach(element => {
            element.calcPerc();
        });
    }

    let binarySearch = function(list, ID){
        let len = list.length;
        let index = Math.ceil(list.length/2)-1;
        let element;
        while(true){
            len = Math.floor(len/2);
            element = list[index];
            if(element.ID === ID){
                list.splice(index, 1);
                return element;
            }
            else if(element.ID < ID){
                index = index + (Math.ceil( len / 2));
            }
            else{
                index = index - (Math.ceil( len / 2));
            }
        }
    }
    
    return{
        getDatabase : () => {
            return db;
        },

        addItem : (type, value, description) =>{
            if(type === "exp"){
                let ID;
                if(db.expCont-length == 0) ID = 0;
                else{
                    ID = db.expCont[db.expCont.length - 1].ID + 1;
                }
                
                element = new expItem(ID, value, description);
                db.expense += value;
                db.expCont.push(element);
                db.updateBuget();
                db.updatePerc();
                element.calcPerc();
                return element;
            }
            else{
                let ID;
                if(db.incCont-length == 0) ID = 0;
                else{
                    ID =db.incCont[db.incCont.length - 1].ID + 1;
                }
                
                let element = new incItem(ID, value, description);
                db.incCont.push(element);
                db.income += value;
                updatePerc();
                db.updateBuget();
                db.updatePerc();
                return element;
                }
                
            },

        removeEl : function(ID, type){
            let el;
            if(type === "exp"){
                el = binarySearch(db.expCont, ID);
                db.expense -= el.val;
                db.updateBuget();
                db.updatePerc();
                
                return db;

            }
            else{
                el = binarySearch(db.incCont, ID);
                db.income -= el.val;
                db.updateBuget();
                db.updatePerc();
                updatePerc();
                return db;
            }
        },

    }; 
})();


//the guicontroller controlls the frontend only used for dom manip
let guiController = (() =>{
    
    let DOMstrings = {
        //various labels
        expensesLabel: '.budget__expenses--value',
        incomeLabel: '.budget__income--value',
        budgetLabel: '.budget__value',
        percentageLabel: '.budget__expenses--percentage',
        dateLabel: '.budget__title--month',
        expensesPercLabel: '.item__percentage',

        //containers for the elements
        container: '.container',
        incomeContainer: '.income__list',
        expensesContainer: '.expenses__list',

        // budget element
        inputDescription: '.add__description',
        inputValue: '.add__value',
        inputType: '.add__type',

        // confirm
        inputBtn: '.add__btn',
        

        }; 
        let months = ["January" ,  "February", 
                    "March", "April" , "May" , 
                    "June" , "July", "August" , 
                    "September", "October", 
                    "November", "December"];


        let updateIncome = income => { // income
            document.querySelector(DOMstrings.incomeLabel).textContent = income;
        }

        let updateExpenses = expense => { // expense
            document.querySelector(DOMstrings.expensesLabel).textContent = expense;
        }

        let updateBuget = budget => { // buget
            document.querySelector(DOMstrings.budgetLabel).textContent = budget;
        }

        let updatePercentage = percentage=> {// outer percentage
            document.querySelector(DOMstrings.percentageLabel).textContent = `${percentage}%`;
        }
        let updatePercentages = expenses =>{ // itempercentages
            if(expenses.length > 0){
                for(let i = 0; i < expenses.length; i ++){
                    document.getElementById(`exp-${expenses[i].ID}`).querySelector(".item__percentage").textContent = `${expenses[i].percentage}%`;
                }
            }
        }

        

        return {
        updateVisuals : (budget, income, expenses, percentage, eElements) => { // a collectormethod to update the whole gui
            updateBuget(budget);
            updateIncome(income);
            updateExpenses(expenses);
            updatePercentage(percentage);
            updatePercentages(eElements);
        },
       
        clearFields : () => { // clears inputfields after submition
            document.querySelector(DOMstrings.inputDescription).value = "";
            document.querySelector(DOMstrings.inputValue).value = "";
            },

        addItem : (item, type) => { // adds htmlelement accordig to newly added databaseelement
            let container , html;
            if(type === "exp"){
                container = DOMstrings.expensesContainer;
                html=  `<div class="item clearfix" id="exp-${item.ID}">
                        <div class="item__description">${item.desc}</div>
                        <div class="right clearfix">
                            <div class="item__value">${item.val}</div>
                            <div class="item__percentage">${item.percentage}%</div>
                            <div class="item__delete">
                                <button class="item__delete--btn"><i class="ion-ios-close-outline"></i></button>
                            </div>
                        </div>
                    </div>`
            }
            else if(type === "inc"){
                container = DOMstrings.incomeContainer;
                html = `<div class="item clearfix" id="inc-${item.ID}">
                        <div class="item__description">${item.desc}</div>
                        <div class="right clearfix">
                            <div class="item__value">${item.val}</div>
                            <div class="item__delete">
                                <button class="item__delete--btn"><i class="ion-ios-close-outline"></i></button>
                            </div>
                        </div>
                    </div>`
            }
            document.querySelector(container).insertAdjacentHTML('beforeend', html);
            
        },

        deleteItem : ID => { // deletes a containerelement by the ID on the click of a deletebutton
            let element = document.getElementById(ID)
            element.parentNode.removeChild(element);
        },
        
        
        setDate : () => { // sets the date of the docment on init
            document.querySelector(".budget__title").textContent =`The budget for ${months[ new Date().getMonth()]} is:`;
        },
        
        getDomstrings : () =>{ // returns the domstrings to controller
            return DOMstrings;
        }
    };

})();

let golobalController = ((dbCtr, domCtr) =>{ // controlls the two parts of the app
    let backend = dbCtr;
    let db = backend.getDatabase();
    let frontend = domCtr;
    let DS = frontend.getDomstrings();

    function addListener(){ // method for submitlistener
        let desc = document.querySelector(DS.inputDescription).value;
        let value = parseInt(document.querySelector(DS.inputValue).value);
        if(desc && value){    
            if(document.querySelector(".add__type").selectedIndex == 0){
                frontend.addItem(backend.addItem("inc", value, desc), "inc");
                }
            else{
                frontend.addItem(backend.addItem("exp", value, desc), "exp");
            }

            frontend.updateVisuals(db.budget, db.income, db.expense, db.percentage, db.expCont);
            frontend.clearFields();
        }
    }

    function deleteItem(itemID){ // function to delete item both from database and frontend
        let item = itemID.split("-");
        let type =item[0];
        let ID = item[1];
        backend.removeEl(parseInt(ID), type);
        frontend.deleteItem(itemID);
        frontend.updateVisuals(db.budget, db.income, db.expense, db.percentage, db.expCont);
    }

    function setupEventlisteners(){ // sets up eventlisteners
        document.querySelector(DS.inputBtn).addEventListener("click", addListener);
        document.querySelector(DS.container).addEventListener("click", event => {
            let itemID = event.target.parentNode.parentNode.parentNode.parentNode.id;
            if(itemID){
                deleteItem(itemID);
            }
            
        })
    }


    return{
        init : () =>{ // initsializes program
            setupEventlisteners();
            frontend.setDate();
        }

        
    };

})(budgetCtr, guiController);
golobalController.init();
