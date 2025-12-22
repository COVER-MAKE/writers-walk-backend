#!/bin/bash
set -euo pipefail

echo "[start] waiting for env + jar"

# env/jar가 생성될 때까지 잠깐 대기 (최대 120초)
for i in {1..120}; do
  if [[ -f /etc/writerswalk.env && -f /home/ubuntu/app.jar ]]; then
    break
  fi
  sleep 1
done

ls -al /etc/writerswalk.env /home/ubuntu/app.jar

echo "[start] reload + restart"
systemctl daemon-reload
systemctl reset-failed writerswalk || true
systemctl enable writerswalk || true
systemctl restart writerswalk

systemctl --no-pager status writerswalk