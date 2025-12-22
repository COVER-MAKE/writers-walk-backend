#!/bin/bash
set -e

echo "=== DEBUGGING START ==="
ls -al /etc/systemd/system/writerswalk.service || echo ">>> [ERROR] Service file is MISSING at destination!"

ls -al /home/ubuntu/writerswalk.service || echo ">>> [WARNING] Source file not found in deployment root"
echo "=== DEBUGGING END ==="

echo "Reload systemd and restart writerswalk"
systemctl daemon-reload
systemctl enable writerswalk || true
systemctl restart writerswalk
systemctl status writerswalk --no-pager