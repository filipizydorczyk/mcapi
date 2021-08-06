TOKEN="${TOKEN:-"94a7b26c-7f4c-44a1-8c8b-b5db49a824e6"}"
PORT="${PORT:-"7000"}"

curl -H Accept:text/event-stream http://localhost:${PORT}/events -H "Authorization: Bearer ${TOKEN}"
