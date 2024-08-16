function convertToLocalTime() {
    const elements = document.querySelectorAll('.convert-time');
    elements.forEach(element => {
        const utcTime = element.getAttribute('data-utc');
        const localTime = new Date(utcTime).toLocaleString();
        element.textContent = localTime;
    });
}

document.addEventListener('DOMContentLoaded', convertToLocalTime);