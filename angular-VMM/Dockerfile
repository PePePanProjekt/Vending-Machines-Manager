FROM node as build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . ./
ARG configuration=angular
RUN npm run build --configuration $configuration
 
FROM docker.io/library/nginx:latest
COPY --from=build /app/dist/angular/ /usr/share/nginx/html
COPY /nginx-custom.conf /etc/nginx/conf.d/default.conf
ENTRYPOINT ["nginx","-g","daemon off;"]