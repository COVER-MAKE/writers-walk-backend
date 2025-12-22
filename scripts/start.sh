#!/bin/bash
set -e

echo "Reload systemd and restart writerswalk"
systemctl daemon-reload
systemctl restart writerswalk
systemctl status writerswalk --no-pager
