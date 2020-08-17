/*
GAME RULES:

- The game has 2 players, playing in rounds
- In each turn, a player rolls a dice as many times as he whishes. Each result get added to his ROUND score
- BUT, if the player rolls a 1, all his ROUND score gets lost. After that, it's the next player's turn
- The player can choose to 'Hold', which means that his ROUND score gets added to his GLBAL score. After that, it's the next player's turn
- The first player to reach 100 points on GLOBAL score wins the game

*/
let current, player, scores, dice, over
let p0N = "PLAYER 1";
let p1N = "PLAYER 2";
newGame();

function newGame(){
        current=0
        player = Math.floor(Math.random()*2);
        document.querySelector(".player-" + player + "-panel").classList.remove('active');
        document.querySelector(".player-" + player + "-panel").classList.add('active');
        scores = [0,0];
        document.querySelector("#score-1").textContent = 0;
        document.querySelector("#score-0").textContent = 0;
        document.querySelector("#current-1").textContent = 0;
        document.querySelector("#current-0").textContent = 0;
        document.querySelector("#name-0").textContent = p0N;
        document.querySelector("#name-1").textContent = p1N;
        document.querySelector('.dice').style.display = 'block';
        over = false;
}

document.querySelector(".btn-roll").addEventListener("click", function(){
    if (!over) {
        dice = Math.floor((Math.random()*6)+1);
    document.querySelector(".dice").src = "dice-"+ dice +".png";
    if(dice > 1) {
        current += dice;
        document.querySelector("#current-" + player).textContent = current;
    }
    else {
        current = 0;
        document.querySelector("#current-" + player).textContent = current;
        document.querySelector(".player-" + player + "-panel").classList.remove('active');
        if (player == 0)player = 1;
        else player = 0;
        document.querySelector(".player-" + player + "-panel").classList.add('active');
    }
    }
});

document.querySelector(".btn-new").addEventListener("click", newGame);

document.querySelector(".btn-hold"). addEventListener("click", function(){
    if(!over){
    scores[player] += current;
    document.querySelector("#score-" + player).textContent = scores[player];
    current = 0;
    document.querySelector("#current-" + player).textContent = current;
    if(scores[0] >= 100){
        document.querySelector("#name-0").textContent = document.querySelector("#name-0").textContent + " er vinneren!";
        over = true;
        document.querySelector('.dice').style.display = 'none';
        return;
    }
    else if (scores[1] >= 100){
        document.querySelector("#name-1").textContent = document.querySelector("#name-1").textContent + " er vinneren!";
        over = true;
        document.querySelector('.dice').style.display = 'none';
        return;
        }
    document.querySelector(".player-" + player + "-panel").classList.remove('active');
    if (player == 0)player = 1;
    else player = 0;
    document.querySelector(".player-" + player + "-panel").classList.add('active');
    }
});

document.querySelector(".navn-0").addEventListener("keypress", function(e){
    if(e.key === "Enter"){
        p0N = document.getElementById("n-0").value;
        document.querySelector("#name-0").textContent =  p0N;
        var element = document.getElementById("n-0");
        element.parentNode.removeChild(element)
        
    }
});
document.querySelector(".navn-1").addEventListener("keypress", function(e){
    if(e.key === "Enter"){
        p1N = document.getElementById("n-1").value;
        document.querySelector("#name-1").textContent = p1N
        var element = document.getElementById("n-1");
        element.parentNode.removeChild(element)
    }
});

