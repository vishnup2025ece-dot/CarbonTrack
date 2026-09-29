const API = "/api";

document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("carbonForm").addEventListener("submit", saveCalculation);
    loadChallenges();
    loadCalculations();
});

async function saveCalculation(event) {
    event.preventDefault();

    const data = {
        householdName: document.getElementById("householdName").value.trim(),
        electricityKwh: Number(document.getElementById("electricityKwh").value) || 0,
        lpgKg: Number(document.getElementById("lpgKg").value) || 0,
        petrolLitres: Number(document.getElementById("petrolLitres").value) || 0,
        dieselLitres: Number(document.getElementById("dieselLitres").value) || 0,
        waterLitres: Number(document.getElementById("waterLitres").value) || 0,
        wasteKg: Number(document.getElementById("wasteKg").value) || 0
    };

    try {
        const response = await fetch(`${API}/calculations`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(data)
        });

        if (!response.ok) throw new Error("Could not save calculation");
        const result = await response.json();

        showResult(result.totalCo2);
        await loadCalculations();
        document.getElementById("status").textContent = "Saved";
    } catch (error) {
        document.getElementById("status").textContent = "API Error";
        alert("Make sure Spring Boot is running and XAMPP MySQL is started.");
    }
}

function showResult(total) {
    document.getElementById("totalCo2").textContent = Number(total).toFixed(2);
    document.getElementById("heroTotal").textContent = Number(total).toFixed(2);

    const width = Math.min((Number(total) / 1000) * 100, 100);
    document.getElementById("meterBar").style.width = `${width}%`;

    let message = "Good start — keep tracking your household activities.";
    if (total > 500) message = "Your estimate is relatively high. Try completing more green challenges.";
    else if (total > 250) message = "You can reduce your footprint by saving energy and transport emissions.";
    else if (total > 0) message = "Nice! Continue with low-carbon household habits.";

    document.getElementById("resultMessage").textContent = message;
}

async function loadCalculations() {
    try {
        const response = await fetch(`${API}/calculations`);
        const records = await response.json();
        const tbody = document.getElementById("calculationTable");

        if (!records.length) {
            tbody.innerHTML = `<tr><td colspan="4">No calculations saved yet.</td></tr>`;
            return;
        }

        tbody.innerHTML = records.slice().reverse().map(item => `
            <tr>
                <td>${escapeHtml(item.householdName || "Unnamed")}</td>
                <td><strong>${Number(item.totalCo2).toFixed(2)}</strong></td>
                <td>${formatDate(item.calculatedAt)}</td>
                <td><button class="delete" onclick="deleteCalculation(${item.id})">Delete</button></td>
            </tr>
        `).join("");
    } catch (e) {
        document.getElementById("calculationTable").innerHTML =
            `<tr><td colspan="4">Start Spring Boot to load saved records.</td></tr>`;
    }
}

async function deleteCalculation(id) {
    if (!confirm("Delete this calculation?")) return;
    await fetch(`${API}/calculations/${id}`, {method: "DELETE"});
    loadCalculations();
}

async function loadChallenges() {
    try {
        const response = await fetch(`${API}/challenges`);
        const challenges = await response.json();
        renderChallenges(challenges);
    } catch (e) {
        document.getElementById("challengeList").innerHTML =
            "<p>Start Spring Boot to load challenges.</p>";
    }
}

function renderChallenges(challenges) {
    let points = 0;
    challenges.forEach(c => { if (c.completed) points += c.points; });

    document.getElementById("points").textContent = points;

    document.getElementById("challengeList").innerHTML = challenges.map(c => `
        <div class="challenge">
            <div>
                <h4>${escapeHtml(c.title)}</h4>
                <p>${escapeHtml(c.description)}</p>
            </div>
            <div>
                <div class="points">+${c.points} pts</div>
                <button class="${c.completed ? "complete" : "secondary"}"
                        onclick="toggleChallenge(${c.id})">
                    ${c.completed ? "Completed" : "Complete"}
                </button>
            </div>
        </div>
    `).join("");
}

async function toggleChallenge(id) {
    await fetch(`${API}/challenges/${id}/toggle`, {method: "POST"});
    loadChallenges();
}

function formatDate(value) {
    if (!value) return "-";
    return new Date(value).toLocaleString("en-IN", {
        dateStyle: "medium",
        timeStyle: "short"
    });
}

function escapeHtml(value) {
    return String(value).replace(/[&<>"']/g, ch => ({
        "&":"&amp;", "<":"&lt;", ">":"&gt;", '"':"&quot;", "'":"&#039;"
    }[ch]));
}
