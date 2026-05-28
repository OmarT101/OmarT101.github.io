// ── Tab Navigation ──────────────────────────────────────────────────────────
const tabs     = document.querySelectorAll('.topnav a');
const sections = document.querySelectorAll('.content-section');

tabs.forEach(tab => {
  tab.addEventListener('click', e => {
    e.preventDefault();

    // Deactivate all tabs and sections
    tabs.forEach(t => t.classList.remove('active'));
    sections.forEach(s => s.classList.remove('active'));

    // Activate clicked tab and matching section
    tab.classList.add('active');
    const id = tab.getAttribute('data-tab');
    document.getElementById(id).classList.add('active');

    // Run section-specific logic
    if (id === 'window')  populateWindowSize();
    if (id === 'browser') populateBrowserInfo();
  });
});

// ── Window Size ──────────────────────────────────────────────────────────────
function populateWindowSize() {
  document.getElementById('windowDisplay').innerHTML =
    'Inner width: <strong>' + window.innerWidth + 'px</strong><br>' +
    'Inner height: <strong>' + window.innerHeight + 'px</strong>';
}

window.addEventListener('resize', () => {
  if (document.getElementById('window').classList.contains('active')) {
    populateWindowSize();
  }
});

// ── Browser Info ─────────────────────────────────────────────────────────────
function makeLi(label, value) {
  const li = document.createElement('li');
  li.innerHTML = '<strong>' + label + ':</strong> ' + value;
  return li;
}

function populateBrowserInfo() {
  const nav = document.getElementById('navigatorList');
  nav.innerHTML = '';
  [
    ['appName',    navigator.appName],
    ['product',    navigator.product],
    ['appVersion', navigator.appVersion],
    ['userAgent',  navigator.userAgent],
    ['platform',   navigator.platform],
    ['language',   navigator.language],
  ].forEach(([k, v]) => nav.appendChild(makeLi(k, v)));

  const win = document.getElementById('windowList');
  win.innerHTML = '';
  [
    ['innerHeight', window.innerHeight + 'px'],
    ['innerWidth',  window.innerWidth  + 'px'],
  ].forEach(([k, v]) => win.appendChild(makeLi(k, v)));

  const scr = document.getElementById('screenList');
  scr.innerHTML = '';
  [
    ['width',       screen.width],
    ['height',      screen.height],
    ['availWidth',  screen.availWidth],
    ['availHeight', screen.availHeight],
    ['colorDepth',  screen.colorDepth],
    ['pixelDepth',  screen.pixelDepth],
  ].forEach(([k, v]) => scr.appendChild(makeLi(k, v)));

  const loc = document.getElementById('locationList');
  loc.innerHTML = '';
  [
    ['href',     location.href],
    ['hostname', location.hostname],
    ['pathname', location.pathname],
    ['protocol', location.protocol],
  ].forEach(([k, v]) => loc.appendChild(makeLi(k, v)));
}

// ── Password Checker ─────────────────────────────────────────────────────────
const passwordInput   = document.getElementById('passwordInput');
const passwordMessage = document.getElementById('passwordMessage');

passwordInput.addEventListener('input', () => {
  if (passwordInput.value === 'cheese') {
    passwordMessage.textContent = 'Password is correct!';
    passwordMessage.style.color = 'green';
  } else {
    passwordMessage.textContent = passwordInput.value.length > 0
      ? 'Password is incorrect!'
      : '';
    passwordMessage.style.color = 'red';
  }
});
