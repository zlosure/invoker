FROM clojure:alpine

RUN apk --no-cache --update add ansible bash curl git ncurses python

WORKDIR /zlosure/

# Install gcloud SDK and kubectl
RUN curl -o google-cloud-sdk.tar.gz https://dl.google.com/dl/cloudsdk/channels/rapid/downloads/google-cloud-sdk-224.0.0-linux-x86_64.tar.gz
RUN tar -zxf google-cloud-sdk.tar.gz && rm -f google-cloud-sdk.tar.gz
RUN ./google-cloud-sdk/bin/gcloud components install kubectl --quiet
ENV PATH=$PATH:/zlosure/google-cloud-sdk/bin/

# Install kubernetes-helm
RUN mkdir kubernetes-helm
RUN curl -o kubernetes-helm.tar.gz https://storage.googleapis.com/kubernetes-helm/helm-v2.12.0-linux-amd64.tar.gz
RUN tar -zxf kubernetes-helm.tar.gz -C kubernetes-helm && rm -f kubernetes-helm.tar.gz
ENV PATH=$PATH:/zlosure/kubernetes-helm/linux-amd64/

ENV PATH=$PATH:/zlosure/invoker/bin

EXPOSE 8080 55555

ADD . invoker

WORKDIR /zlosure/invoker

ENTRYPOINT ["bin/entry-point"]
